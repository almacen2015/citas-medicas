package citasmedicas.model.dto;

public class CitaDTO {
    private Integer id;
    private ClienteDTO clienteDTO;
    private AreaDTO areaDTO;
    private String titulo;
    private String fechaInicio;
    private String fechaFin;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ClienteDTO getClienteDTO() {
        return clienteDTO;
    }

    public void setClienteDTO(ClienteDTO clienteDTO) {
        this.clienteDTO = clienteDTO;
    }

    public AreaDTO getAreaDTO() {
        return areaDTO;
    }

    public void setAreaDTO(AreaDTO areaDTO) {
        this.areaDTO = areaDTO;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }
}
