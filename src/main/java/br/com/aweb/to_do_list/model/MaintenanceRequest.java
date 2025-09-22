package br.com.aweb.to_do_list.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    public enum Urgency {
        BAIXA("Baixa"),
        MEDIA("Média"),
        ALTA("Alta");

        private final String displayName;

        Urgency(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }

    public enum MaintenanceType {
        PREVENTIVA("Preventiva"),
        CORRETIVA("Corretiva"),
        PREDITIVA("Preditiva");

        private final String displayName;

        MaintenanceType(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome do solicitante é obrigatório.")
    @Size(min = 3, max = 100, message = "O nome do solicitante deve ter entre 3 e 100 caracteres.")
    @Column(length = 100, nullable = false)
    private String requesterName;

    @NotBlank(message = "A descrição do problema é obrigatória.")
    @Size(min = 10, max = 500, message = "A descrição do problema deve ter entre 10 e 500 caracteres.")
    @Column(length = 500, nullable = false)
    private String problemDescription;

    @NotNull(message = "O tipo de manutenção é obrigatório.")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MaintenanceType maintenanceType;

    @NotNull(message = "O nível de urgência é obrigatório.")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Urgency urgency;

    @Column(nullable = false)
    private LocalDateTime requestTimestamp = LocalDateTime.now();

    @Column(nullable = true)
    private LocalDateTime finishedTimestamp;
}