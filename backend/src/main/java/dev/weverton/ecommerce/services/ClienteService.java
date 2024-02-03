package dev.weverton.ecommerce.services;

import dev.weverton.ecommerce.domain.Cliente;
import dev.weverton.ecommerce.domain.Cliente;
import dev.weverton.ecommerce.dto.ClienteDTO;
import dev.weverton.ecommerce.exceptions.DataIntegrityException;
import dev.weverton.ecommerce.exceptions.EntityNotFoundException;
import dev.weverton.ecommerce.repositories.ClienteRepository;
import dev.weverton.ecommerce.repositories.EnderecoRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClienteService {

    private ClienteRepository clienteRepository;
    private EnderecoRepository enderecoRepository;

    public ClienteService(ClienteRepository clienteRepository, EnderecoRepository enderecoRepository) {
        this.clienteRepository = clienteRepository;
        this.enderecoRepository = enderecoRepository;
    }


    public List<ClienteDTO> findAll(){
        List<Cliente> listaClientes = clienteRepository.findAll();
        List<ClienteDTO> clientes = listaClientes.stream().map( obj -> new ClienteDTO(obj)).collect(Collectors.toList());
        return clientes;
    }

    public Cliente find(Long id){
        Optional<Cliente> cliente = clienteRepository.findById(id);
        return cliente.orElseThrow(
                () -> new EntityNotFoundException("Cliente(" + id +") não encontrada"));
    }

    @Transactional
    public Cliente store(Cliente clienteObj){
        clienteObj.setId(null);
        Cliente cliente = clienteRepository.save(clienteObj);

        //Salvando lista de endereços
        enderecoRepository.saveAll(clienteObj.getEnderecos());

        return cliente;
    }

    public Cliente update(Cliente clienteObj){
        Cliente clienteToUpdate = find(clienteObj.getId());

        clienteToUpdate.setNome(clienteObj.getNome());
        clienteToUpdate.setEmail(clienteObj.getEmail());

        return clienteRepository.save(clienteToUpdate);
    }

    public void delete(Long id){
        find(id);
        try{
            clienteRepository.deleteById(id);
        }catch (DataIntegrityViolationException e){
            throw new DataIntegrityException("Não é possível deletar esta entidade pois existem dados associados.");
        }
    }
}
