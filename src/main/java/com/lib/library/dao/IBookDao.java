package com.lib.library.dao;

import com.lib.library.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IBookDao extends JpaRepository<Book,Integer> {
    Book findById(int id);
    Page<Book> findByCategory(String category,Pageable pageable);
    Page<Book> findByAnther(String anther, Pageable pageable);
}
