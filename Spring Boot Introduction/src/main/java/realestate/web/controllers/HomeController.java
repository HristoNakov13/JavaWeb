package realestate.web.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import realestate.domain.models.view.OfferListViewModel;
import realestate.services.OfferService;
import realestate.util.HtmlReader;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class HomeController {
    private final String INDEX_PAGE_FILE_PATH = "C:\\Users\\Username\\Desktop\\realestate\\src\\main\\resources\\static\\index.html";
    private final String NO_OFFERS_MESSAGE = "There aren't any offers!";

    private OfferService offerService;
    private HtmlReader htmlReader;
    private ModelMapper modelMapper;

    @Autowired
    public HomeController(OfferService offerService, HtmlReader htmlReader, ModelMapper modelMapper) {
        this.offerService = offerService;
        this.htmlReader = htmlReader;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/")
    @ResponseBody
    public String index() {
        List<OfferListViewModel> allOffers = this.offerService
                .getAllOffers()
                .stream()
                .map(offer -> this.modelMapper.map(offer, OfferListViewModel.class))
                .collect(Collectors.toList());

        String offersTemplate = String.format("<p id=\"message\">%s</p>", NO_OFFERS_MESSAGE);

        if (!allOffers.isEmpty()) {
            offersTemplate = allOffers.stream()
                    .map(offer ->
                            String.format("<div class=\"apartment\"><p>Rent: %s</p>\r\n" +
                                            "<p>Type: %s</p>\r\n" +
                                            "<p>Commission: %s</p>",
                                    offer.getApartmentRate(),
                                    offer.getApartmentType(),
                                    offer.getAgencyCommission()))
                    .collect(Collectors.joining("</div>\r\n")) + "</div>";
        }

        String indexPage = this.htmlReader.getFileContent(INDEX_PAGE_FILE_PATH);

        return indexPage.replace("{{offers}}", offersTemplate);
    }
}