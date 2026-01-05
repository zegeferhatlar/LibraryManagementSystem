package com.zegeferhatlar.library.service;

import com.zegeferhatlar.library.model.Book;
import com.zegeferhatlar.library.model.Librarian;
import com.zegeferhatlar.library.model.Loan;
import com.zegeferhatlar.library.model.Member;
import com.zegeferhatlar.library.model.StudentMember;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.*;

/**
 * Simple CSV-based file storage for books, members and loans.
 * No external libraries are used.
 */
public class FileStorage {

    private final Path dataDir;
    private final Path booksFile;
    private final Path membersFile;
    private final Path loansFile;
    private final Path librariansFile;

    public FileStorage() {
        this.dataDir = Path.of("data");
        this.booksFile = dataDir.resolve("books.csv");
        this.membersFile = dataDir.resolve("members.csv");
        this.loansFile = dataDir.resolve("loans.csv");
        this.librariansFile = dataDir.resolve("librarians.csv");
    }

    public void ensureDataDir() throws IOException {
        if (!Files.exists(dataDir)) {
            Files.createDirectories(dataDir);
        }
    }

    // ------------------- BOOKS -------------------

    public void saveBooks(List<Book> books) throws IOException {
        ensureDataDir();
        try (BufferedWriter writer = Files.newBufferedWriter(booksFile)) {
            writer.write("isbn,title,author,available");
            writer.newLine();

            for (Book b : books) {
                writer.write(
                        csv(b.getIsbn()) + "," + csv(b.getTitle()) + "," + csv(b.getAuthor()) + "," + b.isAvailable());
                writer.newLine();
            }
        }
    }

    public List<Book> loadBooks() throws IOException {
        ensureDataDir();
        if (!Files.exists(booksFile))
            return new ArrayList<>();

        List<Book> books = new ArrayList<>();
        try (BufferedReader reader = Files.newBufferedReader(booksFile)) {
            String line = reader.readLine(); // header
            if (line == null)
                return books;

            while ((line = reader.readLine()) != null) {
                String[] parts = splitCsvLine(line);
                // isbn,title,author,available
                String isbn = uncsv(parts[0]);
                String title = uncsv(parts[1]);
                String author = uncsv(parts[2]);
                boolean available = Boolean.parseBoolean(parts[3]);

                Book book = new Book(isbn, title, author);
                book.setAvailable(available);
                books.add(book);
            }
        }
        return books;
    }

    // ------------------- MEMBERS -------------------

    public void saveMembers(List<Member> members) throws IOException {
        ensureDataDir();
        try (BufferedWriter writer = Files.newBufferedWriter(membersFile)) {
            writer.write("type,id,name,maxBooks");
            writer.newLine();

            for (Member m : members) {
                // Şimdilik sadece StudentMember var:
                String type = (m instanceof StudentMember) ? "STUDENT" : "UNKNOWN";
                writer.write(type + "," + m.getId() + "," + csv(m.getName()) + "," + m.getMaxBooks());
                writer.newLine();
            }
        }
    }

    public List<Member> loadMembers() throws IOException {
        ensureDataDir();
        if (!Files.exists(membersFile))
            return new ArrayList<>();

        List<Member> members = new ArrayList<>();
        try (BufferedReader reader = Files.newBufferedReader(membersFile)) {
            String line = reader.readLine(); // header
            if (line == null)
                return members;

            while ((line = reader.readLine()) != null) {
                String[] parts = splitCsvLine(line);

                // En az: type,id,name olmalı
                if (parts.length < 3)
                    continue;

                // type,id,name,maxBooks
                String type = parts[0];
                int id;
                try {
                    id = Integer.parseInt(parts[1]);
                } catch (NumberFormatException e) {
                    continue; // bozuk satır
                }

                String name = uncsv(parts[2]);

                int maxBooks = 5; // default
                if (parts.length >= 4) {
                    try {
                        maxBooks = Integer.parseInt(parts[3]);
                    } catch (NumberFormatException ignored) {
                        // default kalsın
                    }
                }

                if ("STUDENT".equalsIgnoreCase(type)) {
                    StudentMember sm = new StudentMember(id, name);
                    sm.setMaxBooks(maxBooks);
                    members.add(sm);
                }
            }
        }
        return members;
    }

    // ------------------- LOANS -------------------

    public void saveLoans(List<Loan> loans) throws IOException {
        ensureDataDir();
        try (BufferedWriter writer = Files.newBufferedWriter(loansFile)) {
            writer.write("isbn,memberId,loanDate,returnDate");
            writer.newLine();

            for (Loan l : loans) {
                String isbn = l.getBook().getIsbn();
                int memberId = l.getMember().getId();
                String loanDate = l.getLoanDate().toString(); // ISO
                String returnDate = (l.getReturnDate() == null) ? "" : l.getReturnDate().toString();

                writer.write(csv(isbn) + "," + memberId + "," + loanDate + "," + csv(returnDate));
                writer.newLine();
            }
        }
    }

    public List<Loan> loadLoans(List<Book> books, List<Member> members) throws IOException {
        ensureDataDir();
        if (!Files.exists(loansFile))
            return new ArrayList<>();

        Map<String, Book> bookMap = new HashMap<>();
        for (Book b : books)
            bookMap.put(b.getIsbn(), b);

        Map<Integer, Member> memberMap = new HashMap<>();
        for (Member m : members)
            memberMap.put(m.getId(), m);

        List<Loan> loans = new ArrayList<>();

        try (BufferedReader reader = Files.newBufferedReader(loansFile)) {
            String line = reader.readLine(); // header
            if (line == null)
                return loans;

            while ((line = reader.readLine()) != null) {
                String[] parts = splitCsvLine(line);
                // isbn,memberId,loanDate,returnDate
                String isbn = uncsv(parts[0]);
                int memberId = Integer.parseInt(parts[1]);
                LocalDate loanDate = LocalDate.parse(parts[2]);
                String returnDateRaw = (parts.length >= 4) ? uncsv(parts[3]) : "";

                Book book = bookMap.get(isbn);
                Member member = memberMap.get(memberId);

                if (book == null || member == null) {
                    // Eksik kayıt varsa bu loan'ı atla
                    continue;
                }

                Loan loan = new Loan(book, member, loanDate);

                if (returnDateRaw != null && !returnDateRaw.isBlank()) {
                    loan.setReturnDate(LocalDate.parse(returnDateRaw));
                    book.setAvailable(true);
                } else {
                    // aktif loan ise book unavailable olmalı
                    book.setAvailable(false);
                }

                loans.add(loan);
            }
        }

        return loans;
    }

    // ------------------- LIBRARIANS -------------------

    public void saveLibrarians(List<Librarian> librarians) throws IOException {
        ensureDataDir();
        try (BufferedWriter writer = Files.newBufferedWriter(librariansFile)) {
            writer.write("username,password,name");
            writer.newLine();

            for (Librarian l : librarians) {
                writer.write(
                        csv(l.getUsername()) + "," + csv(l.getPassword()) + "," + csv(l.getName()));
                writer.newLine();
            }
        }
    }

    public List<Librarian> loadLibrarians() throws IOException {
        ensureDataDir();
        if (!Files.exists(librariansFile))
            return new ArrayList<>();

        List<Librarian> librarians = new ArrayList<>();
        try (BufferedReader reader = Files.newBufferedReader(librariansFile)) {
            String line = reader.readLine(); // header
            if (line == null)
                return librarians;

            while ((line = reader.readLine()) != null) {
                String[] parts = splitCsvLine(line);
                // username,password,name
                if (parts.length < 3)
                    continue;

                String username = uncsv(parts[0]);
                String password = uncsv(parts[1]);
                String name = uncsv(parts[2]);

                if (username.isBlank() || password.isBlank()) {
                    continue; // bozuk satır
                }

                Librarian librarian = new Librarian(username, password, name);
                librarians.add(librarian);
            }
        }
        return librarians;
    }

    // ------------------- CSV helpers -------------------

    private String csv(String value) {
        if (value == null)
            return "\"\"";
        String escaped = value.replace("\"", "\"\"");
        return "\"" + escaped + "\"";
    }

    private String uncsv(String value) {
        if (value == null)
            return "";
        String v = value.trim();
        if (v.startsWith("\"") && v.endsWith("\"") && v.length() >= 2) {
            v = v.substring(1, v.length() - 1);
        }
        return v.replace("\"\"", "\"");
    }

    /**
     * Very small CSV splitter for our quoted format.
     * Assumes each field is either quoted or plain, separated by commas.
     */
    private String[] splitCsvLine(String line) {
        List<String> parts = new ArrayList<>();
        StringBuilder cur = new StringBuilder();
        boolean inQuotes = false;

        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);

            if (c == '"') {
                inQuotes = !inQuotes;
                cur.append(c);
            } else if (c == ',' && !inQuotes) {
                parts.add(cur.toString());
                cur.setLength(0);
            } else {
                cur.append(c);
            }
        }
        parts.add(cur.toString());
        return parts.toArray(new String[0]);
    }
}
