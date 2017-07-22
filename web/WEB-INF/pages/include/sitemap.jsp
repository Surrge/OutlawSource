<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div id="siteMap" class="non-mobile">
	<ul>
		<li>
			<div class="sitelink">
				<a href="<c:url value="/" />">
					<span>Home</span>
				</a>
			</div>
		</li>
		<li>
			<span>Resources</span>
			<ul>
				<li>
					<div class="sitelink">
						<a href="<c:url value="/Resources/Summary" />">
							<span>Summary</span>
						</a>
					</div>
				</li>
				<li>
					<div class="sitelink">
						<a href="<c:url value="/Resources/Chart" />">
							<span>Chart</span>
						</a>
					</div>
				</li>
				<li>
					<div class="sitelink">
						<a href="<c:url value="/Resources/Alert" />">
							<span>Alerts</span>
						</a>
					</div>
				</li>
				<li>
					<div class="sitelink">
						<a href="<c:url value="/Resources/Stats" />">
							<span>Game Stats</span>
						</a>
					</div>
				</li>
			</ul>
		</li>
		<li>
			<div class="sitelink">
				<a href="/Register">
					<span>Register</span>
				</a>
			</div>
		</li>
	</ul>
</div>
