package br.edu.ufcg.computacao.si1.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.ufcg.computacao.si1.model.form.UsuarioForm;
import br.edu.ufcg.computacao.si1.service.UsuarioServiceImpl;
import br.edu.ufcg.computacao.si1.util.Util;

@Controller
public class CadastroController {
		
    @Autowired
    private UsuarioServiceImpl usuarioService;

    @RequestMapping(value = Util.ROTA_CADASTRO, method = RequestMethod.GET)
    public ModelAndView getPageCadastro(UsuarioForm usuarioForm){
        ModelAndView model = new ModelAndView();
        model.setViewName(Util.CADASTRO);

        return model;
    }

    @RequestMapping(value = Util.ROTA_CADASTRO, method = RequestMethod.POST)
    public ModelAndView cadastro(@Valid UsuarioForm usuarioForm, BindingResult result, RedirectAttributes attributes){
        if(result.hasErrors()){
            return getPageCadastro(usuarioForm);
        }
        if (usuarioService.getByEmail(usuarioForm.getEmail()).isPresent()){
            attributes.addFlashAttribute(Util.ERROR, Util.MENSAGEM_EMAIL_EM_USO);
            return new ModelAndView(Util.REDIRECT + Util.ROTA_CADASTRO);
        }

        usuarioService.create(usuarioForm);

        attributes.addFlashAttribute(Util.MENSAGEM, Util.MENSAGEM_USUARIO_CADASTRO_SUCESSO);
        return new ModelAndView(Util.REDIRECT + Util.ROTA_CADASTRO);
    }
}
