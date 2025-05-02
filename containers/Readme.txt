docker build -t pliot:latest .

#
#   Creazione registry locale
#
docker run -d -p 5000:5000 --name registry --restart always registry:2
#
# Tag nel registry locale
#
docker tag pliot:latest localhost:5000/pliot:latest
#
# Push dell'immagine
#
docker push localhost:5000/pliot:latest

