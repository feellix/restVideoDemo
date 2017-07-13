package io.feellix.restvideodemo.domain.video;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CodecInformation {
    private String codec;
    private Long bitRate;
}
