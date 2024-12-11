package com.example.demo.dto.compraDTO;

import java.util.List;

import com.example.demo.model.Administrador;
import com.example.demo.model.Produto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompraResponseDTO {
    
  private Long id; 

  private String nomeDoProduto;


  private int quantidade;


  private int notaFiscal;


  private String dataDeEntrada;

  @Column(length = 14)
  private String cnpj;

  @Column(length = 16)
  private Double valor;

    

}
