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

