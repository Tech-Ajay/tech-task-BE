package com.acme.bookmanagement.config;

import com.acme.bookmanagement.model.Book;
import com.acme.bookmanagement.repository.BookRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.List;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initDatabase(BookRepository repository) {
        return args -> {
            repository.saveAll(List.of(
                // Morisaki Bookshop Series
                new Book(null, 
                    "With Love from the Morisaki Bookshop",
                    "Satoshi Yagisawa",
                    LocalDate.of(2024, 1, 20),
                    "A beautiful, collectable hardback that brings together Days at the Morisaki Bookshop and More Days at the Morisaki Bookshop in one volume. A young woman, lost and heartbroken. Her eccentric, optimist uncle.",
                    "https://cdn11.bigcommerce.com/s-aweq463/images/stencil/670x670/products/56213/141313/9781786584960__96915.1730646319.jpg?c=2"),
                
                new Book(null,
                    "Days at the Morisaki Bookshop",
                    "Satoshi Yagisawa",
                    LocalDate.of(2023, 9, 20),
                    "On a quiet corner in an old wooden building lies a shop filled with hundreds of second-hand books. Twenty-five-year-old Takako has never liked reading, although the Morisaki bookshop has been in her family for three generations.",
                    "https://cdn11.bigcommerce.com/s-aweq463/images/stencil/670x670/products/48005/121736/9781786583239__25172.1688057331.jpg?c=2"),
                
                // Jujutsu Kaisen Series
                new Book(null,
                    "Jujutsu Kaisen, Vol. 20",
                    "Gege Akutami",
                    LocalDate.of(2023, 10, 20),
                    "To gain the power he needs to save his friend from a cursed spirit, Yuji Itadori swallows a piece of a demon, only to find himself caught in the midst of a horrific war of the supernatural!",
                    "https://cdn11.bigcommerce.com/s-aweq463/products/48652/images/122949/9781974738748--1-__25540.1692699057.550.659.jpg?c=2"),
                
                new Book(null,
                    "Jujutsu Kaisen: Thorny Road at Dawn",
                    "Gege Akutami",
                    LocalDate.of(2023, 8, 20),
                    "Resurrect the tense conflict between the Jujutsu Sorcerers and the cursed spirits in this collection of original stories. Sorcery and demon curses abound in this collection of stories spun from the world of Jujutsu Kaisen.",
                    "https://cdn11.bigcommerce.com/s-aweq463/images/stencil/670x670/products/48254/122286/9781974732562__75528.1689609437.jpg?c=2"),
                
                new Book(null,
                    "Jujutsu Kaisen, Vol. 11",
                    "Gege Akutami",
                    LocalDate.of(2023, 6, 20),
                    "To gain the power he needs to save his friend from a cursed spirit, Yuji Itadori swallows a piece of a demon, only to find himself caught in the midst of a horrific war of the supernatural!",
                    "https://cdn11.bigcommerce.com/s-aweq463/images/stencil/670x670/products/40663/105653/9781974722846__00980.1648470035.jpg?c=2"),
                
                new Book(null,
                    "Jujutsu Kaisen, Vol. 12",
                    "Gege Akutami",
                    LocalDate.of(2023, 1, 20),
                    "The incident in Shibuya becomes dire when Toji Zen'in reappears! Meanwhile, Mei Mei confronts the traitorous Geto on a subway platform, and Nanami's furious over the casualties suffered by the assistant managers.",
                    "https://cdn11.bigcommerce.com/s-aweq463/images/stencil/670x670/products/39211/103358/9781974722853__81593.1639065315.jpg?c=2"),
                
                new Book(null,
                    "Jujutsu Kaisen, Vol. 18",
                    "Gege Akutami",
                    LocalDate.of(2023, 4, 20),
                    "To gain the power he needs to save his friend from a cursed spirit, Yuji Itadori swallows a piece of a demon, only to find himself caught in the midst of a horrific war of the supernatural!",
                    "https://cdn11.bigcommerce.com/s-aweq463/images/stencil/670x670/products/48201/122228/9781974734399__29627.1689072256.jpg?c=2"),
                
                new Book(null,
                    "Jujutsu Kaisen, Vol. 19",
                    "Gege Akutami",
                    LocalDate.of(2023, 5, 20),
                    "In order to add a rule that will serve as a loophole in the Culling Game, Itadori and Fushiguro set their sights on a player named Hiromi Higuruma, who has 100 points.",
                    "https://cdn11.bigcommerce.com/s-aweq463/images/stencil/670x670/products/46400/116915/9781974736270__51614.1680709177.jpg?c=2"),
                
                // Demon Slayer Series
                new Book(null,
                    "Demon Slayer: Kimetsu no Yaiba, Vol. 11",
                    "Koyoharu Gotouge",
                    LocalDate.of(2022, 5, 20),
                    "To gain the power he needs to save his friend from a cursed spirit, Yuji Itadori swallows a piece of a demon, only to find himself caught in the midst of a horrific war of the supernatural!",
                    "https://cdn11.bigcommerce.com/s-aweq463/products/44659/images/113450/9781974706488__00245.1669463205.550.659.jpg?c=2"),
                
                new Book(null,
                    "Demon Slayer: Kimetsu no Yaiba, Vol. 21",
                    "Koyoharu Gotouge",
                    LocalDate.of(2022, 6, 20),
                    "To gain the power he needs to save his friend from a cursed spirit, Yuji Itadori swallows a piece of a demon, only to find himself caught in the midst of a horrific war of the supernatural!",
                    "https://cdn11.bigcommerce.com/s-aweq463/products/42877/images/110319/cf126a4129631e7f35762a18980f76668cb6a0fb__99175.1659958132.550.659.jpg?c=2"),
                
                new Book(null,
                    "Demon Slayer: Kimetsu no Yaiba, Vol. 09",
                    "Koyoharu Gotouge",
                    LocalDate.of(2022, 6, 20),
                    "To gain the power he needs to save his friend from a cursed spirit, Yuji Itadori swallows a piece of a demon, only to find himself caught in the midst of a horrific war of the supernatural!",
                    "https://cdn11.bigcommerce.com/s-aweq463/products/42310/images/109123/9781974704439__22828.1656665455.550.659.jpg?c=2"),
                
                new Book(null,
                    "Demon Slayer: Kimetsu no Yaiba, Vol. 14",
                    "Koyoharu Gotouge",
                    LocalDate.of(2022, 3, 20),
                    "To gain the power he needs to save his friend from a cursed spirit, Yuji Itadori swallows a piece of a demon, only to find himself caught in the midst of a horrific war of the supernatural!",
                    "https://cdn11.bigcommerce.com/s-aweq463/products/42210/images/108969/9781974711147__27566.1656156405.550.659.jpg?c=2"),
                
                new Book(null,
                    "Demon Slayer: Kimetsu no Yaiba, Vol. 10",
                    "Koyoharu Gotouge",
                    LocalDate.of(2022, 9, 20),
                    "To gain the power he needs to save his friend from a cursed spirit, Yuji Itadori swallows a piece of a demon, only to find himself caught in the midst of a horrific war of the supernatural!",
                    "https://cdn11.bigcommerce.com/s-aweq463/products/42209/images/108968/9781974704552__67279.1656156395.550.659.jpg?c=2"),
                
                new Book(null,
                    "Demon Slayer: Kimetsu no Yaiba, Vol. 12",
                    "Koyoharu Gotouge",
                    LocalDate.of(2022, 10, 20),
                    "To gain the power he needs to save his friend from a cursed spirit, Yuji Itadori swallows a piece of a demon, only to find himself caught in the midst of a horrific war of the supernatural!",
                    "https://cdn11.bigcommerce.com/s-aweq463/products/41623/images/107894/9781974711123__77963.1652803760.550.659.jpg?c=2"),
                
                new Book(null,
                    "Demon Slayer: Kimetsu no Yaiba, Vol. 08",
                    "Koyoharu Gotouge",
                    LocalDate.of(2022, 10, 20),
                    "To gain the power he needs to save his friend from a cursed spirit, Yuji Itadori swallows a piece of a demon, only to find himself caught in the midst of a horrific war of the supernatural!",
                    "https://cdn11.bigcommerce.com/s-aweq463/products/40613/images/105554/9781974704422__04499.1648050906.550.659.jpg?c=2"),
                
                new Book(null,
                    "Demon Slayer: Kimetsu no Yaiba, Vol. 07",
                    "Koyoharu Gotouge",
                    LocalDate.of(2022, 11, 20),
                    "To gain the power he needs to save his friend from a cursed spirit, Yuji Itadori swallows a piece of a demon, only to find himself caught in the midst of a horrific war of the supernatural!",
                    "https://cdn11.bigcommerce.com/s-aweq463/products/39771/images/104164/9781974704415__05438.1644235632.550.659.jpg?c=2"),
                
                new Book(null,
                    "Demon Slayer: Kimetsu no Yaiba, Vol. 02",
                    "Koyoharu Gotouge",
                    LocalDate.of(2023, 1, 20),
                    "To gain the power he needs to save his friend from a cursed spirit, Yuji Itadori swallows a piece of a demon, only to find himself caught in the midst of a horrific war of the supernatural!",
                    "https://cdn11.bigcommerce.com/s-aweq463/products/39689/images/104025/9781974700530__97853.1643894556.550.659.jpg?c=2")
            ));
        };
    }
} 