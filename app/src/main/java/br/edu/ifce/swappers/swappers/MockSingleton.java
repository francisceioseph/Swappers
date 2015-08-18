package br.edu.ifce.swappers.swappers;

import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;

import br.edu.ifce.swappers.swappers.model.Book;
import br.edu.ifce.swappers.swappers.model.Comment;
import br.edu.ifce.swappers.swappers.model.Place;

/**
 * A singleton to concetrate all mocked methods
 */
public enum MockSingleton {
    INSTANCE;

    public ArrayList<Place> createMockedPlaceDataSource(double latitude, double longitude) {
        ArrayList<Place> dataSource = new ArrayList<>();


        for (int i = 0; i < 5; i++){
            Place place = new Place(latitude,longitude);
            place.setNamePlace("Shopping Benfica");
            place.setAdressPlace("Av. Carapinima, 2081, Benfica");
            dataSource.add(place);

        }
        return dataSource;
    }

    public ArrayList<Book> createMockedBookDataSource() {
        ArrayList<Book> dataSource = new ArrayList<>();

        for (int i = 0; i < 5; i++){
            dataSource.add(new Book("A Book", "An Author", "A Publisher", 2.1f, 4.2f));

        }
        return dataSource;
    }

    public ArrayList<Comment> createMockedReadersCommentsSource(){
        ArrayList<Comment> dataSource = new ArrayList<>();

        for (int i = 0; i < 5; i++){
            dataSource.add(new Comment("Francis Thomas", "Posted at Apr. 24, 2015", "Lorem ipsum dolor sit amet, mutat dicit scriptorem in sed, mea tractatos instructior ad, virtute discere maluisset ei has. Delenit denique laboramus ei pri, est id assentior necessitatibus, ne copiosae concludaturque eum. Usu everti deseruisse cu. Quo ut periculis efficiendi, malis nonumes voluptaria te eam, mei debitis epicurei an. An vim movet quaerendum consequuntur, ut suas legendos vix. In vis quodsi verear quaeque, ex vero dignissim nam, idque eruditi sapientem sed ad.\n" +
                    "\n" +
                    "Pro cu graece timeam. Feugiat interesset necessitatibus vel ut, cu liber ponderum pri, congue persequeris cu nec. Qui deleniti invenire ad, eu quot omnium singulis mei. Te ipsum corpora vis, mundi appetere periculis id nec. Ad graeci quidam sit, sed eu rebum feugiat.\n" +
                    "\n" +
                    "Vim at errem soluta, lorem antiopam efficiendi te qui, tale fastidii dignissim at qui. Minimum accumsan vix te. Duo te delenit theophrastus, eam tale persius salutandi ut, pri eleifend oportere id. Persequeris conclusionemque pro at, pro animal scripserit cu, omnium veritus oporteat te sed. Posse tacimates deterruisset an usu."));

        }
        return dataSource;
    }


}
