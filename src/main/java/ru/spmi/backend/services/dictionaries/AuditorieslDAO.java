package ru.spmi.backend.services.dictionaries;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.spmi.backend.entities.dictionaries.DAuditories;
import ru.spmi.backend.repositories.dictionaries.AuditoriesRepository;

@Service
public class AuditorieslDAO {
    @Autowired
    private AuditoriesRepository auditoriesRepository;

    public DAuditories getListAuditories( ) {

        var list = auditoriesRepository.getAllBy();

        return list;
    }
}
