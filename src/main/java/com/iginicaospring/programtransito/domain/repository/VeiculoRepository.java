package com.iginicaospring.programtransito.domain.repository;

import com.iginicaospring.programtransito.domain.model.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // diz que é um componente de spring
public interface VeiculoRepository extends JpaRepository<Veiculo, Long> {
}
