package dev.weverton.ecommerce.services.validation;

import dev.weverton.ecommerce.domain.enums.TipoCliente;
import dev.weverton.ecommerce.dto.ClienteStoreDTO;
import dev.weverton.ecommerce.exceptions.FieldMessage;
import dev.weverton.ecommerce.repositories.ClienteRepository;
import dev.weverton.ecommerce.services.validation.utils.BR;

import java.util.ArrayList;
import java.util.List;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ClienteStoreValidator implements ConstraintValidator<ClienteStoreValidation, ClienteStoreDTO> {

    private ClienteRepository clienteRepository;

    public ClienteStoreValidator(ClienteRepository clienteRepository){
        this.clienteRepository = clienteRepository;
    }

    @Override
    public void initialize(ClienteStoreValidation ann) {
    }
    @Override
    public boolean isValid(ClienteStoreDTO objDto, ConstraintValidatorContext context) {
        List<FieldMessage> list = new ArrayList<>();

        if(objDto.getTipo().equals(TipoCliente.PESSOA_FISICA.getCod()) && !BR.isValidCPF(objDto.getDocumento())){
            list.add(new FieldMessage("documento", "CPF Inválido"));
        }

        if(objDto.getTipo().equals(TipoCliente.PESSOA_JURIDICA.getCod()) && !BR.isValidCNPJ(objDto.getDocumento())){
            list.add(new FieldMessage("documento", "CNPJ Inválido"));
        }

        if(clienteRepository.findByEmail(objDto.getEmail()).isPresent()){
            list.add(new FieldMessage("email", "E-mail já cadastrado"));
        }

        for (FieldMessage e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage())
                    .addPropertyNode(e.getField()).addConstraintViolation();
        }
        return list.isEmpty();
    }
}