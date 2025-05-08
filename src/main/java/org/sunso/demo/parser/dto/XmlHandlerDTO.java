package org.sunso.demo.parser.dto;

import lombok.Data;
import org.sunso.demo.parser.file.Metadata;

/**
 * @author sunso520
 * @Title:XmlHandlerDTO
 * @Description: <br>
 * @Created on 2025/4/7 11:00
 */
@Data
public class XmlHandlerDTO {
    private Metadata metadata;

    public static XmlHandlerDTO of() {
        XmlHandlerDTO xmlHandlerDTO = new XmlHandlerDTO();
        xmlHandlerDTO.setMetadata(Metadata.getMetadata());
        return xmlHandlerDTO;
    }
}
