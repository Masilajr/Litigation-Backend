package com.LDLS.Litigation.Project.BillingModule.Services;

import com.LDLS.Litigation.Project.BillingModule.Repositories.ClientRepository;
import com.LDLS.Litigation.Project.BillingModule.Entities.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Service
public class ClientService {

    @PersistenceContext
    private EntityManager entityManager;

    public Client getClientById(Long clientId) throws NoResultException {
        TypedQuery<Client> query = entityManager.createQuery("SELECT c FROM Client c WHERE c.id = :id", Client.class); // Specify return type
        query.setParameter("id", clientId);
        return query.getSingleResult();
    }

    @Autowired
    private ClientRepository clientRepository;

    public Client saveClient(Client client) {
        return clientRepository.save(client);
    }

    public Client findClientById(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client not found with id: " + id));
    }

    public List<Client> findAllClients() {
        return clientRepository.findAll();
    }
}



