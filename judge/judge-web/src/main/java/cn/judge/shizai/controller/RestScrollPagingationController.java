package cn.judge.shizai.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.judge.shizai.pagingation.Pageingation;

@RestController
public class RestScrollPagingationController {



	/**
	 * @param form
	 * @param result
	 * @return
	 */
	@RequestMapping(value = "/content")
	public String content(@RequestBody Pageingation data) throws Exception {
		System.out.println(data.getStart());

		StringBuilder sb = new StringBuilder();
		sb.append("<li style=\"opacity:0;-moz-opacity: 0;filter: alpha(opacity=0);\"><p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc elementum elementum felis. Quisque porta turpis nec eros consectetur lacinia. Pellentesque sagittis adipiscing egestas. </p></li>");
		sb.append("<li style=\"opacity:0;-moz-opacity: 0;filter: alpha(opacity=0);\"><p>Aliquam dapibus tincidunt odio. Phasellus volutpat dui nec ante volutpat euismod. </p></li>");
		sb.append("<li style=\"opacity:0;-moz-opacity: 0;filter: alpha(opacity=0);\"><p>Aliquam dapibus tincidunt odio. Phasellus volutpat dui nec ante volutpat euismod. </p></li>");
		sb.append("<li style=\"opacity:0;-moz-opacity: 0;filter: alpha(opacity=0);\"><p>Aliquam dapibus tincidunt odio. Phasellus volutpat dui nec ante volutpat euismod. </p></li>");
		sb.append("<li style=\"opacity:0;-moz-opacity: 0;filter: alpha(opacity=0);\"><p>Aliquam dapibus tincidunt odio. Phasellus volutpat dui nec ante volutpat euismod. </p></li>");
		sb.append("<li style=\"opacity:0;-moz-opacity: 0;filter: alpha(opacity=0);\"><p>Aliquam dapibus tincidunt odio. Phasellus volutpat dui nec ante volutpat euismod. </p></li>");
		sb.append("<li style=\"opacity:0;-moz-opacity: 0;filter: alpha(opacity=0);\"><p>Aliquam dapibus tincidunt odio. Phasellus volutpat dui nec ante volutpat euismod. </p></li>");
		sb.append("<li style=\"opacity:0;-moz-opacity: 0;filter: alpha(opacity=0);\"><p>Aliquam dapibus tincidunt odio. Phasellus volutpat dui nec ante volutpat euismod. </p></li>");
		sb.append("<li style=\"opacity:0;-moz-opacity: 0;filter: alpha(opacity=0);\"><p>Aliquam dapibus tincidunt odio. Phasellus volutpat dui nec ante volutpat euismod. </p></li>");
		sb.append("<li style=\"opacity:0;-moz-opacity: 0;filter: alpha(opacity=0);\"><p>Aliquam dapibus tincidunt odio. Phasellus volutpat dui nec ante volutpat euismod. </p></li>");
		sb.append("<li style=\"opacity:0;-moz-opacity: 0;filter: alpha(opacity=0);\"><p>Aliquam dapibus tincidunt odio. Phasellus volutpat dui nec ante volutpat euismod. </p></li>");
		sb.append("<li style=\"opacity:0;-moz-opacity: 0;filter: alpha(opacity=0);\"><p>Aliquam dapibus tincidunt odio. Phasellus volutpat dui nec ante volutpat euismod. </p></li>");
//		    	<li style="opacity:0;-moz-opacity: 0;filter: alpha(opacity=0);"><p>Phasellus vehicula turpis nec dui facilisis eget condimentum risus ullamcorper. Nunc imperdiet, tortor ultrices aliquam eleifend, nisl turpis venenatis dui, at vestibulum magna tellus in tortor. </p></li>
//		    	<li style="opacity:0;-moz-opacity: 0;filter: alpha(opacity=0);"><p>Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Mauris tincidunt nisi in tortor tincidunt ut ullamcorper lectus dapibus.  </p></li>
//		        <li style="opacity:0;-moz-opacity: 0;filter: alpha(opacity=0);"><p>Aenean interdum dui vitae purus molestie nec placerat nibh semper. Maecenas ultrices elementum dapibus. Aenean feugiat, metus in mattis mattis, justo nisi dignissim libero, ac volutpat dui nibh quis metus.</p></li>
//		        <li style="opacity:0;-moz-opacity: 0;filter: alpha(opacity=0);"><p> Morbi eget tristique dui. Vivamus nec turpis eu nisi euismod accumsan sed in tortor. Maecenas laoreet leo ut tortor viverra facilisis.</p></li>
//		        <li style="opacity:0;-moz-opacity: 0;filter: alpha(opacity=0);"><p> Morbi eget tristique dui. Vivamus nec turpis eu nisi euismod accumsan sed in tortor. Maecenas laoreet leo ut tortor viverra facilisis.</p></li>
//		        <li style="opacity:0;-moz-opacity: 0;filter: alpha(opacity=0);"><p> Morbi eget tristique dui. Vivamus nec turpis eu nisi euismod accumsan sed in tortor. Maecenas laoreet leo ut tortor viverra facilisis.</p></li>
//		        <li style="opacity:0;-moz-opacity: 0;filter: alpha(opacity=0);"><p> Morbi eget tristique dui. Vivamus nec turpis eu nisi euismod accumsan sed in tortor. Maecenas laoreet leo ut tortor viverra facilisis.</p></li>
//		        <li style="opacity:0;-moz-opacity: 0;filter: alpha(opacity=0);"><p> Morbi eget tristique dui. Vivamus nec turpis eu nisi euismod accumsan sed in tortor. Maecenas laoreet leo ut tortor viverra facilisis.</p></li>
//		        <li style="opacity:0;-moz-opacity: 0;filter: alpha(opacity=0);"><p> Morbi eget tristique dui. Vivamus nec turpis eu nisi euismod accumsan sed in tortor. Maecenas laoreet leo ut tortor viverra facilisis.</p></li>
//		        <li style="opacity:0;-moz-opacity: 0;filter: alpha(opacity=0);"><p> Morbi eget tristique dui. Vivamus nec turpis eu nisi euismod accumsan sed in tortor. Maecenas laoreet leo ut tortor viverra facilisis.</p></li>
//		        <li style="opacity:0;-moz-opacity: 0;filter: alpha(opacity=0);"><p> Morbi eget tristique dui. Vivamus nec turpis eu nisi euismod accumsan sed in tortor. Maecenas laoreet leo ut tortor viverra facilisis.</p></li>
//		        <li style="opacity:0;-moz-opacity: 0;filter: alpha(opacity=0);"><p> Morbi eget tristique dui. Vivamus nec turpis eu nisi euismod accumsan sed in tortor. Maecenas laoreet leo ut tortor viverra facilisis.</p></li>
//		        <li style="opacity:0;-moz-opacity: 0;filter: alpha(opacity=0);"><p> Morbi eget tristique dui. Vivamus nec turpis eu nisi euismod accumsan sed in tortor. Maecenas laoreet leo ut tortor viverra facilisis.</p></li>
//		        <li style="opacity:0;-moz-opacity: 0;filter: alpha(opacity=0);"><p> Morbi eget tristique dui. Vivamus nec turpis eu nisi euismod accumsan sed in tortor. Maecenas laoreet leo ut tortor viverra facilisis.</p></li>
//		        <li style="opacity:0;-moz-opacity: 0;filter: alpha(opacity=0);"><p> Morbi eget tristique dui. Vivamus nec turpis eu nisi euismod accumsan sed in tortor. Maecenas laoreet leo ut tortor viverra facilisis.</p></li>
//				";
		return sb.toString();
	}
}
