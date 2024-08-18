package ro.ps.auctionhousemail.service.mail;

import ro.ps.auctionhousemail.dto.mail.MailRequestDTO;
import ro.ps.auctionhousemail.dto.mail.MailResponseDTO;

public interface MailService {

    MailResponseDTO sendMail(MailRequestDTO mailRequestDTO);
}
