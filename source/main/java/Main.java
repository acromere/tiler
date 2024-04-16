import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class Main {

	public static void main( String[] args ) {
		Path path = Paths.get( System.getProperty( "user.home" ), "PDF" );

		String project = "Endeavor I";
		char sheet = 'A';
		int row = 1;
		int col = 1;
		try( Stream<Path> stream = Files.list( path ) ) {
			// Sort by name
			List<Path> files = stream.sorted().toList();

			// Sort by last modified
			//List<Path> files = stream.sorted( ( a, b ) -> (int)(a.toFile().lastModified() - b.toFile().lastModified()) ).toList();

			for( Path source : files ) {
				if( Files.isDirectory( source ) ) continue;

				String filename = project + " - Sheet " + sheet + "-" + row + "-" + col + ".pdf";
				Path target = path.resolve( filename );
				if( !Objects.equals( source, target ) ) System.out.println( source + " > " + target );

				Files.move( source, target );

				col++;
				if( col > 4 ) {
					col = 1;
					row++;
				}
				if( row > 2 ) {
					row = 1;
					sheet++;
				}
			}
		} catch( Exception exception ) {
			exception.printStackTrace();
		}
	}

}
