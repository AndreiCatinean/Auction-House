package proj.auctionhousebackend.service.mail;


import proj.auctionhousebackend.dto.mail.MailRequestDTO;
import proj.auctionhousebackend.dto.mail.MailResponseDTO;

public interface MailService {

    MailResponseDTO sendMail(MailRequestDTO mailRequestDTO);
}
