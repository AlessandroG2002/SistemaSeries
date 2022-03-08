package br.unitins.series.converter.jsf;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.unitins.series.model.Avaliacao;
import br.unitins.series.repository.AvaliacaoRepository;


@FacesConverter(forClass = Avaliacao.class)
public class AvaliacaoConverter implements Converter<Avaliacao> {

	@Override
	public Avaliacao getAsObject(FacesContext context, UIComponent component, String value) {
		if (value == null || value.isEmpty())
			return null;
		
		AvaliacaoRepository repo = new AvaliacaoRepository();
		try {
			return repo.findById(Integer.valueOf(value.trim()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Avaliacao avaliacao) {
		if (avaliacao == null || avaliacao.getId() == null)
			return null;
		
		return avaliacao.getId().toString();
	}

}