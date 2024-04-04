window.addEventListener('DOMContentLoaded', function() {
    fetchProductionData();
});

function fetchProductionData() {
    var token = localStorage.getItem('token')
          var requestOptions = {
          method: 'GET',
          headers: {
              "Authorization": "Bearer "+ token
          },redirect: 'follow'}

    fetch("http://localhost:8080/api/v1/productionTracker/getAllProductionTrackerByIsEnabled", requestOptions)
    .then(response => response.json())
    .then(result => {
        // Verileri al ve tabloya ekle
        populateProductionTable(result.data);
    })
    .catch(error => {
        console.log(error);
    });
}

// Üretim verilerini tabloya ekleyen fonksiyon
function populateProductionTable(data) {
    const productionList = document.querySelector('.production-list');

    // Önce mevcut verileri temizle
    productionList.innerHTML = '';
    // Verileri döngü ile tabloya ekle
    data.forEach(item => {
        const row = document.createElement('tr');
        row.innerHTML = `
            <td>${item.id}</td>
            <td>${item.productName}</td>
            <td>${item.productType}</td>
            <td>${item.size}</td>
            <td>${item.quantity}</td>
            <td>${item.description}</td>
            <td>
                <button class="btn btn-danger btn-sm delete" data-id="${item.id}">Delete</button>
            </td>
        `;
        productionList.appendChild(row);
    });

    // Delete butonlarına tıklama olayını ekle
    const deleteButtons = document.querySelectorAll('.delete');
    deleteButtons.forEach(button => {
        button.addEventListener('click', function() {
            const productionTrackerId = this.getAttribute('data-id');
            deleteProductionTracker(productionTrackerId);
        });
    });
}

// Üretim verisini silen fonksiyon
function deleteProductionTracker(productionTrackerId) {
    var token = localStorage.getItem('token');
    var requestOptions = {
        method: 'POST',
        headers: {
            "Authorization": "Bearer " + token
        },
        redirect: 'follow'
    };

    fetch(`http://localhost:8080/api/v1/productionTracker/deleteProductionTracker?productionTrackerId=${productionTrackerId}`, requestOptions)
    .then(response => response.json())
    .then(result => {
        console.log(result);
     if(result.success == false){
       showPopup(result.message);
     } else {
       showPopup(result.message, result.success);
     if(result.success == true) {
         setTimeout(function() {
           fetchProductionData();
         }, 3000); // 3000 milisaniye = 3 saniye
     }
     }
 })
    .catch(error => console.log('error', error));
}




const form = document.getElementById("production-form")
        form.addEventListener('submit', function(e){
          e.preventDefault();
  
          var productName = document.getElementById("inputProductName").value;
          var productType = document.getElementById("inputProductType").value;
          var size = document.getElementById("inputSize").value;
          var quantity = document.getElementById("inputQualityControl").value;
          var description = document.getElementById("inputDescription").value;

          var formData = {
              "productName": productName,
              "size": size,
              "description": description,
              "productType": productType,
              "quantity": quantity 
          };
          var token = localStorage.getItem('token')
          var requestOptions = {
          method: 'POST',
          headers: {
              "Content-Type": "application/json",
              "Authorization": "Bearer "+ token
          },
          body: JSON.stringify(formData),
          redirect: 'follow'
      };
  
      // Fetch isteği
          fetch("http://localhost:8080/api/v1/productionTracker/addNewProductionTracker", requestOptions)
          .then(response => response.json())
              .then(result => {
                     console.log(result);
                  if(result.success == false){
                    showPopup(result.message);
                  } else {
                    showPopup(result.message, result.success);
                  if(result.success == true) {
                      setTimeout(function() {
                        fetchProductionData();
                      }, 3000); // 3000 milisaniye = 3 saniye
                  }
                  }
              })
              .catch(error => {
                  console.log(error);
              });
      });
  
function showPopup(message, isSuccess) {
    var popup = document.getElementById('popup');
    popup.innerHTML = `<span style="font-size: 24px; ${isSuccess ? 'color: #28a745;' : 'color: #dc3545;'}">${isSuccess ? '✔' : '✖'}</span>
                        <p style="font-size: 18px; margin-top: 10px;">${message}</p>`;
    popup.style.display = 'block';
    setTimeout(function() {
        popup.style.display = 'none';
    }, 3000); // Popup'ı 3 saniye sonra otomatik olarak gizle
}