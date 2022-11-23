package com.lib.library.service.impl;

import com.lib.library.dao.IBookDao;
import com.lib.library.model.Book;
import com.lib.library.service.IBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BookService implements IBookService {

    @Autowired
    IBookDao bookDao;

    @Override
    public void newBook(String name, double price, int count, String anther, String category,int initialQuantity) {
        Book book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setCount(count);
        book.setInitialQuantity(initialQuantity);
        book.setAnther(anther);
        book.setCategory(category);
        bookDao.save(book);
    }

    @Override
    public Page<Book> getBookList(Pageable pageable) {
        return bookDao.findAll(pageable);
    }

    @Override
    public Page<Book> getBookListByAnther(String anther, Pageable pageable) {
        return bookDao.findByAnther(anther,pageable);
    }

    @Override
    public Page<Book> getBookListByCategory(String category, Pageable pageable) {
        return bookDao.findByCategory(category,pageable);
    }

    @Override
    public Book getBookById(int id) {
        return bookDao.findById(id);
    }

    @Override
    public Book getBookByName(String name) {
        return bookDao.findByName(name);
    }
}
