package br.unitins.series.converter.jsf;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.unitins.series.model.Estudio;
import br.unitins.series.repository.EstudioRepository;


@FacesConverter(forClass = Estudio.class)
public class EstudioConverter implements Converter<Estudio> {

	@Override
	public Estudio getAsObject(FacesContext context, UIComponent component, String value) {
		if (value == null || value.isEmpty())
			return null;
		
		EstudioRepository repo = new EstudioRepository();
		try {
			return repo.findById(Integer.valueOf(value.trim()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Estudio estado) {
		if (estado == null || estado.getId() == null)
			return null;
		
		return estado.getId().toString();
	}

}