package org.iqu.webcrawler.others;

import javax.ws.rs.HEAD;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

/**
 * Class returns 200 if System runs correctly.
 * 
 * @author Razvan Rosu
 *
 */
@Path("/ping")
public class PingService {

	@HEAD
	public Response ping() {

		return Response.status(200).build();

	}

}
