package com.lib.library.service;

import com.lib.library.model.History;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IHistoryService {
    void borrowBook(int bookId,int userId);
    void returnBook(int bookId,int userId);
    Page<History> getHistoryByBookId(int bookId,Pageable pageable);
    Page<History> getHistoryByUserId(int userId, Pageable pageable);
    Page<History> getHistoryList(Pageable pageable);
}
