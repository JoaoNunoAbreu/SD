import java.util.List;

public class Musica {

    private int id;
    private String titulo;
    private String interprete;
    private String ano;
    private List<Character> etiquetas;

    public Musica(int id, String titulo, String interprete, String ano, List<Character> etiquetas) {
        this.id = id;
        this.titulo = titulo;
        this.interprete = interprete;
        this.ano = ano;
        this.etiquetas = etiquetas;
    }

    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getInterprete() {
        return interprete;
    }

    public String getAno() {
        return ano;
    }

    public List<Character> getEtiquetas() {
        return etiquetas;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setInterprete(String interprete) {
        this.interprete = interprete;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }

    public void setEtiquetas(List<Character> etiquetas) {
        this.etiquetas = etiquetas;
    }

    @Override
    public String toString() {
        return "Musica{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", interprete='" + interprete + '\'' +
                ", ano='" + ano + '\'' +
                ", etiquetas=" + etiquetas +
                '}';
    }
}
