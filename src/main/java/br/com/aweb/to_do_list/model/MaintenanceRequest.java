package br.com.aweb.to_do_list.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@ToString
public class MaintenanceRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome do solicitante é obrigatório.")
    @Column(length = 100, nullable = false)
    private String requesterName;

    @NotBlank(message = "A descrição do problema é obrigatória.")
    @Column(length = 500, nullable = false)
    private String problemDescription;

    @NotBlank(message = "O tipo de manutenção é obrigatório.")
    @Column(nullable = false)
    private String maintenanceType;

    @NotBlank(message = "O nível de urgência é obrigatório.")
    @Column(nullable = false)
    private String urgency;

    @Column(nullable = false)
    private LocalDateTime requestTimestamp = LocalDateTime.now();

    @Column(nullable = true)
    private LocalDateTime finishedTimestamp;
}