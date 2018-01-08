package com.example.mydiary.dao;

import com.example.mydiary.entity.Entry;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface EntryRepository extends CrudRepository<Entry,String>{
    List<Entry> findTop10ByMarkedForDeleteFalseAndIdOrderByDateOfEntryDesc(String id);
    List<Entry> findByMarkedForDeleteFalseAndProfileIdAndDateOfEntry(String profileId, Date date);
    List<Entry> findByMarkedForDeleteFalseAndId(String id);
}
