// Create a JSP file to display the form and the result of currency conversion
<!DOCTYPE html>
<html>
<head>
    <title>Currency Converter</title>
</head>
<body>
    <h1>Currency Converter</h1>
    <form method="post">
        <label for="amount">Amount:</label>
        <input type="number" name="amount" id="amount"><br><br>
        <label for="from_currency">From:</label>
        <select name="from_currency" id="from_currency">
            <c:forEach items="${currencies}" var="currency">
                <option value="${currency.name}">${currency.name}</option>
            </c:forEach>
        </select><br><br>
        <label for="to_currency">To:</label>
        <select name="to_currency" id="to_currency">
            <c:forEach items="${currencies}" var="currency">
                <option value="${currency.name}">${currency.name}</option>
            </c:forEach>
        </select><br><br>
        <input type="submit" value="Convert">
    </form>
    <c:if test="${not empty result}">
        <p>${amount} ${from_currency} = ${result} ${to_currency}</p>
    </c:if>
</body>
</html>