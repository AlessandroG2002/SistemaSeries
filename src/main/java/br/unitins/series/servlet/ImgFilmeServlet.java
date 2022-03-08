package br.unitins.series.servlet;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/img-filme")
public class ImgFilmeServlet extends HttpServlet {

	private static final long serialVersionUID = 1172640224100761704L;
	// /images
	public static final String PATH_IMAGES = File.separator + "images";
	// /images/filme
	public static final String PATH_IMAGES_FILME = PATH_IMAGES + File.separator + "filme";
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // recebendo o nome da imagem 
        String nome = request.getParameter("nome");
        
        // /home/janio/images/filme/
        String diretorio = System.getProperty("user.home") + File.separator + PATH_IMAGES_FILME + File.separator;
        
        // montando a imagem com e endereco do servidor
        // /home/janio/images/filme/2.png
        File image = new File(diretorio + nome);
        
        // se o nome da imagem for nulo ou se a imagem nao existir, enviar uma imagem padrao
        if ((nome == null) || (!image.exists())) {
            File img = new File(diretorio + "sem_foto.png");
            // montando objeto de resposta
            response.reset();
            response.setContentType("image");
            response.setHeader("Content-Length", String.valueOf(img.length()));
            //atribuindo a imagem no obejto de resposta
            Files.copy(img.toPath(), response.getOutputStream());
            return;
        }
 
        // obtendo o tipo do arquivo (contentType) e verificando se eh realmente uma imagem
        // caso nao seja uma imagem, retorna um erro 404
        String contentType = getServletContext().getMimeType(image.getName());
        if ((contentType == null) || (!contentType.startsWith("image/"))) {
            response.sendError(404);
            return;
        }
        // montando objeto de resposta
        response.reset();
        response.setContentType(contentType);
        response.setHeader("Content-Length", String.valueOf(image.length()));
        //atribuindo a imagem no obejto de resposta
        Files.copy(image.toPath(), response.getOutputStream());
    }

}

