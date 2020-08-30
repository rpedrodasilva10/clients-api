package br.clientsapi.clientsapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.clientsapi.clientsapi.entity.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {
}