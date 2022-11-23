package com.lib.library.service.impl;

import com.lib.library.dao.IHistoryDao;
import com.lib.library.model.History;
import com.lib.library.service.IHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class HistoryService implements IHistoryService {


    @Autowired
    IHistoryDao historyDao;


    @Override
    public void borrowBook(int bookId,int userId) {
        History history = new History();
        history.setBookId(bookId);
        history.setUserId(userId);
        history.setType("借取");
        history.setCount(1);
        history.setDate(new Date());
        historyDao.save(history);

    }

    @Override
    public void returnBook(int bookId,int userId) {
        History history = new History();
        history.setBookId(bookId);
        history.setUserId(userId);
        history.setType("归还");
        history.setCount(1);
        history.setDate(new Date());
        historyDao.save(history);
    }

    @Override
    public void newBook(int bookId, int userId,int count) {
        History history = new History();
        history.setBookId(bookId);
        history.setId(userId);
        history.setDate(new Date());
        history.setCount(count);
        history.setType("上架");
        historyDao.save(history);
    }

    @Override
    public Page<History> getHistoryByBookId(int bookId, Pageable pageable) {
        return historyDao.findById(bookId,pageable);
    }

    @Override
    public Page<History> getHistoryByUserId(int userId, Pageable pageable) {
        return historyDao.findByUserId(userId, pageable);
    }

    @Override
    public Page<History> getHistoryList(Pageable pageable) {
        return historyDao.findAll(pageable);
    }
}
