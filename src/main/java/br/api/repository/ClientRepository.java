package br.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.api.entity.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {
}