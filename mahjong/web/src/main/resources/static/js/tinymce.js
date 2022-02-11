tinymce.init({            
            selector: "textarea",
            language: "ja",            
            plugins: [            
                "advlist autolink lists link image preview ",
                "table ",
                //'autoresize',              
              ],
          menu : {
                file: {title: 'File', items: 'newdocument'},
                view: {title: 'View', items: 'preview'},
                edit: {title: 'Edit', items: 'undo redo | cut copy | selectall'},
                insert: {title: 'Insert', items: 'image link'},
                format: {title: 'Format', items: 'bold italic underline strikethrough forecolor backcolor'},
            },
          toolbar: 'undo redo | bold italic underline strikethrough |forecolor backcolor | link image|bullist numlist| preview',
          forced_root_block : '',
          statusbar : false,

          height: 300,
          table_column_resizing: 'resizetable',
          table_default_styles: {
                width: '50%'
              },
          paste_as_text: true,        
          paste_data_images: true,
          contextmenu: false,

        branding: false,          
        image_title: true,
        automatic_uploads: true,
        lists_indent_on_tab: false,

        file_picker_types: 'image',
        file_picker_callback: function (cb, value, meta) {
          var input = document.createElement('input');
          input.setAttribute('type', 'file');
          input.setAttribute('accept', 'image/*');
          input.onchange = function () {
            var file = this.files[0];

            var reader = new FileReader();
            reader.onload = function () {             
              var id = 'blobid' + (new Date()).getTime();
              var blobCache =  tinymce.activeEditor.editorUpload.blobCache;
              var base64 = reader.result.split(',')[1];
              var blobInfo = blobCache.create(id, file, base64);
              blobCache.add(blobInfo);
              cb(blobInfo.blobUri(), { title: file.name });
            };
            reader.readAsDataURL(file);
          };

          input.click();
        },
        content_style: 'body { font-family:Helvetica,Arial,sans-serif; font-size:14px }'
      });