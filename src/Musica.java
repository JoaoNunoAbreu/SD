import java.util.List;

public class Musica {

    private int id;
    private String titulo;
    private String interprete;
    private Integer ano;
    private List<String> etiquetas;

    public Musica(int id, String titulo, String interprete, Integer ano, List<String> etiquetas) {
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

    public Integer getAno() {
        return ano;
    }

    public List<String> getEtiquetas() {
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

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public void setEtiquetas(List<String> etiquetas) {
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
