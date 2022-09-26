import json
import random

with open('dataset.json', encoding='UTF-8') as f:
  data = json.load(f)


ids = []
ids_col = []
ids_mem = []

# estas são hardcoded e retiradas das imagens inseridas manualmente no strapi.
ids_img = [{"$oid" : "60d72fb75a46b239283bb8d2"}, {"$oid" : "60d72fb85a46b239283bb8d3"},{"$oid" : "60d72fb95a46b239283bb8d4"},{"$oid" : "60d72fb95a46b239283bb8d5"},{"$oid" : "60d72fb95a46b239283bb8d6"},{"$oid" : "60d72fb95a46b239283bb8d7"},{"$oid" : "60d72fb95a46b239283bb8d8"},{"$oid" : "60d72fb95a46b239283bb8d9"},{"$oid" : "60d72fba5a46b239283bb8da"},{"$oid" : "60d72fba5a46b239283bb8db"},{"$oid" : "60d72fba5a46b239283bb8dc"},{"$oid" : "60d72fba5a46b239283bb8e0"},{"$oid" : "60d72fbb5a46b239283bb8e1"}]
ids_videos = [{"$oid" : "60d7378f5a46b239283bb8e5"},{"$oid" : "60d7378f5a46b239283bb8e6"},{"$oid" : "60d7378f5a46b239283bb8e7"}]
ids_profilePic = [{"$oid" : "60d72fba5a46b239283bb8dd"},{"$oid" : "60d72fba5a46b239283bb8de"},{"$oid" : "60d72fba5a46b239283bb8df"}]
ids_collectionPic = [{"$oid" : "60d72fb55a46b239283bb8c9"},{"$oid" : "60d72fb65a46b239283bb8ca"},{"$oid" : "60d72fb65a46b239283bb8cb"},{"$oid" : "60d72fb65a46b239283bb8cc"},{"$oid" : "60d72fb65a46b239283bb8cd"},{"$oid" : "60d72fb75a46b239283bb8ce"},{"$oid" : "60d72fb75a46b239283bb8cf"},{"$oid" : "60d72fb75a46b239283bb8d0"},{"$oid" : "60d72fb75a46b239283bb8d1"}]


tags = ["love","happy","summer","friends","nature","art","style","travel","fitness","fun","family","food","sun","music","beach","lifestyle","night","funny","work","inspiration","holiday","blessed"]

def writeUsers(f):
    size = len(data['user'])
    count = 1
    f.write("[\n")
    for user in data['user']:
        user['date_of_birth'] = { "$date" : user['date_of_birth'] }
        ids.append(user['_id'])
        if count%2==0:  
            user['profile_picture'] = random.choice(ids_profilePic)
        json.dump(user, f, ensure_ascii=False)
        if (count<200):
            count+=1
            f.write(",\n")
    f.write("\n]")
 

with open('users.json', 'w', encoding='UTF-8') as outfile:
    writeUsers(outfile)



##########################################################



def writeCollections(f):
    size = len(data['collection'])
    count = 1
    f.write("[\n")
    for collection in data['collection']:
        ids_col.append(collection['_id'])
        collection['utilizador'] = random.choice(ids)
        if count%2==0:  
            collection['collection_picture'] = random.choice(ids_collectionPic)
        json.dump(collection, f, ensure_ascii=False)
        if (count<200):
            count+=1
            f.write(",\n")
    f.write("\n]")


with open('collections.json', 'w', encoding='UTF-8') as outfile:
    writeCollections(outfile)



##########################################################



def writeMemories(f):
    size = len(data['memory'])
    count = 1
    f.write("[\n")
    for memory in data['memory']:
        memory['date_of_memory'] = { "$date" : memory['date_of_memory'] }
        ids_mem.append(memory['_id'])
        memory['utilizador'] = random.choice(ids)
        ltags = []
        i = 0
        j = random.randint(0,5)
        while i < j:
            item = random.choice(tags)
            if item not in ltags:
                ltags.append(item)
            i = i+1
        memory['tags'] = ltags
        collections = []
        i = 0
        j = random.randint(0,3)
        while i < j:
            item = random.choice(ids_col)
            if item not in collections:
                collections.append(item)
            i = i+1
        memory['collections'] = collections
        images = []
        i = 0
        j = random.randint(1,4)
        while i < j:
            item = random.choice(ids_img)
            if item not in images:
                images.append(item)
            i = i+1
        memory['images'] = images
        videos = []
        i = 0
        j = random.randint(0,2)
        while i < j:
            item = random.choice(ids_videos)
            if item not in videos:
                videos.append(item)
            i = i+1
        memory['videos'] = videos
        json.dump(memory, f, ensure_ascii=False)
        if (count<500):
            count+=1
            f.write(",\n")
    f.write("\n]")


with open('memories.json', 'w', encoding='UTF-8') as outfile:
    writeMemories(outfile)



##########################################################



def writePeople(f):   
    size = len(data['person'])
    count = 1
    f.write("[\n")
    for person in data['person']:
        if "date_of_birth" in person:
            person['date_of_birth'] = { "$date" : person['date_of_birth'] }
        if "date_of_death" in person:
            person['date_of_death'] = { "$date" : person['date_of_death'] }
        memories = []
        i = 0
        j = random.randint(0,3)
        while i < j:
            item = random.choice(ids_mem)
            if item not in memories:
                memories.append(item)
            i = i+1
        person['memories'] = memories
        json.dump(person, f, ensure_ascii=False)
        if (count<300):
            count+=1
            f.write(",\n")
    f.write("\n]")

with open('people.json', 'w', encoding='UTF-8') as outfile:
    writePeople(outfile)