package tvarfalvi.reportapplication.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tvarfalvi.reportapplication.common.ListingDataValidator;
import tvarfalvi.reportapplication.repository.ListingRepository;

import java.util.UUID;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ListingServiceTest {

    @InjectMocks
    private ListingService listingService;
    @Mock
    private ListingRepository listingRepository;
    @Mock
    private ListingDataValidator listingDataValidator;

    @Test
    public void testClearListingTable() {
        listingService.clearListingTable();
        verify(listingRepository, times(1)).deleteAll();
    }

    @Test
    public void testDeleteFromDatabase() {
        UUID uuid = UUID.randomUUID();

        when(listingRepository.existsById(uuid)).thenReturn(true);
        listingService.deleteFromDatabase(uuid);

        verify(listingRepository, times(1)).deleteById(uuid);
    }

}
