package br.edu.ifce.swappers.swappers;

import java.util.ArrayList;

import br.edu.ifce.swappers.swappers.model.Book;

/**
 * A singleton to concetrate all mocked methods
 */
public enum MockSingleton {
    INSTANCE;

    public ArrayList<Book> createMockedBookDataSource() {
        ArrayList<Book> dataSource = new ArrayList<>();

        for (int i = 0; i < 30; i++)
            dataSource.add(new Book("A Book", "An Author", "A Publisher", 2.1f, 4.2f));

        return dataSource;
    }
}
