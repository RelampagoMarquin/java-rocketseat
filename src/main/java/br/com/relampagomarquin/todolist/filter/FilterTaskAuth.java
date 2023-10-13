package br.com.relampagomarquin.todolist.filter;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.relampagomarquin.todolist.repository.IUserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class FilterTaskAuth extends OncePerRequestFilter {

    @Autowired
    private IUserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        var path = request.getServletPath();
        if(path.startsWith("/tasks/")){
            var auth = request.getHeader("Authorization");

            var encoded = auth.substring("Basic".length()).trim();

            byte[] authDecode = Base64.getDecoder().decode(encoded);

            var string = new String(authDecode);

            String[] credential = string.split(":");
            String username = credential[0];
            String password = credential[1];
            
            var user = this.userRepository.findByUsername(username);
            if(user == null){
                response.sendError(404, "Usuário não existe");
            }else{
                var verify = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());
                if(verify.verified){
                    request.setAttribute("idUser", user.getId());
                    filterChain.doFilter(request, response);
                }else{
                    response.sendError(401, "Não autorizado");
                }
            }
        }else{
            filterChain.doFilter(request, response);
        }
        

    }

}
