package csuite.mvc.entidades;

import io.jsonwebtoken.Claims;

public class Login {
    private String id;
    private Claims jwt;


    public Login(String id, Claims jwt) {
        this.id = id;
        this.jwt = jwt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Claims getJwt() {
        return jwt;
    }

    public void setJwt(Claims jwt) {
        this.jwt = jwt;
    }
}
