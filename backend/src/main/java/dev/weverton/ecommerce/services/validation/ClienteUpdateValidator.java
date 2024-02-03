package dev.weverton.ecommerce.services.validation;

import dev.weverton.ecommerce.domain.Cliente;
import dev.weverton.ecommerce.domain.enums.TipoCliente;
import dev.weverton.ecommerce.dto.ClienteDTO;
import dev.weverton.ecommerce.dto.ClienteStoreDTO;
import dev.weverton.ecommerce.exceptions.FieldMessage;
import dev.weverton.ecommerce.repositories.ClienteRepository;
import dev.weverton.ecommerce.services.validation.utils.BR;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ClienteUpdateValidator implements ConstraintValidator<ClienteUpdateValidation, ClienteDTO> {

    private ClienteRepository clienteRepository;
    private HttpServletRequest request;

    public ClienteUpdateValidator(ClienteRepository clienteRepository, HttpServletRequest request){
        this.clienteRepository = clienteRepository;
        this.request = request;
    }

    @Override
    public void initialize(ClienteUpdateValidation ann) {
    }
    @Override
    public boolean isValid(ClienteDTO objDto, ConstraintValidatorContext context) {
        List<FieldMessage> list = new ArrayList<>();


        //Injetado o Request, e pego os atributos da URI convertendo pra um Map (chave: valor);
        @SuppressWarnings("unchecked")
        Map<String, String> map = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        Long uriId = Long.parseLong(map.get("id"));


        Optional<Cliente> cliente = clienteRepository.findByEmail(objDto.getEmail());
        if(cliente.isPresent()){
            if(!cliente.get().getId().equals(uriId)){
                list.add(new FieldMessage("email", "E-mail já cadastrado"));
            }
        }

        for (FieldMessage e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage())
                    .addPropertyNode(e.getField()).addConstraintViolation();
        }
        return list.isEmpty();
    }
}