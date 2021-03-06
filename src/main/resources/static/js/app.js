//function to add and remove images in image form
function readURL(input) {
    if (input.files && input.files[0]) {
        var reader = new FileReader();

        reader.onload = function (e) {
            $('#thumbnail')
                .attr('src', e.target.result)
                .width(200)
                .height(200)
            $('#removeImageInUse')
                .attr('src', e.target.result)
                .width(0)
                .height(0);
            $('#removePathInUse')
                .text("");
            $('#removeTextInUse')
                .text("");
            $('#newText')
                .text("(New Image)");
        };

        reader.readAsDataURL(input.files[0]);
    }
}

//toggle button
function toggleFunction() {
    var x = document.getElementById("gal");
    if (x.style.display === "none") {
        x.style.display = "grid";
    } else {
        x.style.display = "none";
    }

    var x2 = document.getElementById("sli");
    if (x2.style.display === "block") {
        x2.style.display = "none";
    } else {
        x2.style.display = "block";
    }

    var x3 = document.getElementById("galText");
    if (x3.style.display === "none") {
        x3.style.display = "block";
    } else {
        x3.style.display = "none";
    }

    var x4 = document.getElementById("toggle-button");
    if (x4.innerText === "Go to Gallery") {
        x4.innerText = "Go to Slideshow";
    } else {
        x4.innerText = "Go to Gallery";
    }

}


//function to show starting image in slideshow
function plusDivs(n) {
    showDivs(slideIndex += n);
}

//function to slide through slideshow
function showDivs(n) {
    var i;
    var x = document.getElementsByClassName("mySlides");
    if (n > x.length) {
        slideIndex = 1
    }
    if (n < 1) {
        slideIndex = x.length
    }
    for (i = 0; i < x.length; i++) {
        x[i].style.display = "none";
    }
    x[slideIndex - 1].style.display = "block";

    var i2;
    var x2 = document.getElementsByClassName("imageSliderName");
    if (n > x2.length) {
        slideIndex = 1
    }
    if (n < 1) {
        slideIndex = x2.length
    }
    for (i2 = 0; i2 < x2.length; i2++) {
        x2[i2].style.display = "none";
    }
    x2[slideIndex - 1].style.display = "block";
}

//dark mode function
function modeFunction() {

    var x = document.getElementById("mode");
    if (x.innerText === "Light Mode") {
        x.innerText = "Dark Mode";
    } else {
        x.innerText = "Light Mode";
    }


    if (document.body.style.backgroundColor === 'gray') {
        document.body.style.backgroundColor = 'white';
    } else {
        document.body.style.backgroundColor = 'gray';
    }

    var x2 = document.getElementById("newImage");
    if (x2.style.color === 'darkblue') {
        x2.style.color = '#007bff';
    } else {
        x2.style.color = 'darkblue';
    }

}
