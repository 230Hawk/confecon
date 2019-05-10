package com.bracode.confecon.services.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import com.bracode.confecon.domain.Usuario;
import com.bracode.confecon.domain.dto.UsuarioDTO;
import com.bracode.confecon.repositories.UsuarioRepository;
import com.bracode.confecon.resources.exception.FieldMessage;



public class UsuarioUpdateValidator implements ConstraintValidator<UsuarioUpdate, UsuarioDTO> {
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Override
	public void initialize(UsuarioUpdate ann) {
	}

	@Override
	public boolean isValid(UsuarioDTO objDto, ConstraintValidatorContext context) {
		

		@SuppressWarnings("unchecked")
		Map<String, String> map = (Map<String, String>)request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		Integer uriId = Integer.parseInt(map.get("id"));	
		
		Usuario usuarioAux = usuarioRepository.findByEmail(objDto.getEmail());
		
		List<FieldMessage> list = new ArrayList<>();
		
		if(usuarioAux != null  && !usuarioAux.getId().equals(uriId)) {
			list.add(new FieldMessage("email", "Email já cadastrado."));
		}
		
		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}