document.getElementById('toggleInfo').addEventListener('click', function() {
    var infoSection = document.getElementById('infoSection');
    if (infoSection.style.display === "none" || infoSection.style.display === "") {
      infoSection.style.display = "block";
    } else {
      infoSection.style.display = "none";
    }
});