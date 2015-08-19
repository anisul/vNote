<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page import="com.grasshopper.vnote.util.Utils" %>
<%@ page session="false"%>
		<hr>
		<footer>
			<p>&copy; 2013 Grasshopper Development. All Rights Reserved.</p>
			<p>Version: <%= Utils.APP_VERSION %></p>
		</footer>
	</div>
	<!--/.fluid-container-->
	</body>
</html>
