package bg.guardiankiller.moviessocialapp.model.entity;

import bg.guardiankiller.moviessocialapp.model.dto.Language;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(
        name = "i18n_strings",
        uniqueConstraints={@UniqueConstraint(columnNames={"placeholder", "lang"})})
@NoArgsConstructor
public class I18nString {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private UUID placeholder;

    @Column(name = "val", nullable = false)
    private String value;

    @Enumerated(EnumType.STRING)
    @Column(name = "lang", nullable = false)
    private Language language;

    public I18nString(Language lang, String value) {
        this.language = lang;
        this.value = value;
    }
}
