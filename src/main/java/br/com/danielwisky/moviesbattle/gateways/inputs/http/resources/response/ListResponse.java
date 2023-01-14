package br.com.danielwisky.moviesbattle.gateways.inputs.http.resources.response;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@Data
@NoArgsConstructor
public class ListResponse<T extends Serializable> implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  private List<T> items;
  private Integer page;
  private Integer size;
  private Integer totalPages;
  private Long totalElements;

  public ListResponse(final Page<T> page) {
    this.items = page.getContent();
    this.page = page.getNumber();
    this.size = page.getSize();
    this.totalPages = page.getTotalPages();
    this.totalElements = page.getTotalElements();
  }
}
