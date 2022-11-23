package com.lib.library.service;

import com.lib.library.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IBookService {
    void newBook(String name,double price,int count,String anther,String category,int initialQuantity);
    Page<Book> getBookList(Pageable pageable);
    Page<Book> getBookListByAnther(String anther,Pageable pageable);
    Page<Book> getBookListByCategory(String category,Pageable pageable);
    Book getBookById(int id);
    Book getBookByName(String name);
}
