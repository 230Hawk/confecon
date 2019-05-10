package com.bracode.confecon.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.bracode.confecon.domain.Usuario;
import com.bracode.confecon.domain.dto.UsuarioNewDTO;
import com.bracode.confecon.repositories.UsuarioRepository;
import com.bracode.confecon.resources.exception.FieldMessage;
import com.bracode.confecon.services.validation.utils.BR;



public class UsuarioInsertValidator implements ConstraintValidator<UsuarioInsert, UsuarioNewDTO> {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Override
	public void initialize(UsuarioInsert ann) {
	}

	@Override
	public boolean isValid(UsuarioNewDTO objDto, ConstraintValidatorContext context) {
		List<FieldMessage> list = new ArrayList<>();

		if ( !BR.isValidCPF(objDto.getCpf())) {
			list.add(new FieldMessage("cpf", "CPF Inválido"));
		}
		
		
		Usuario usuarioAux = usuarioRepository.findByEmail(objDto.getEmail());
		
		if(usuarioAux != null) {
			list.add(new FieldMessage("email", "Email já cadastrado"));
		}
		
		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}