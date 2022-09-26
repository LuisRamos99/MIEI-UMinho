const { parseMultipartData, sanitizeEntity } = require('strapi-utils');

module.exports = {

  async updateSpecial(ctx) {
    const { id } = ctx.params;
    let entity;
    entity = await strapi.services.memory.update({ id }, {$push : {collections: ctx.request.body.idCollection}});
    return sanitizeEntity(entity, { model: strapi.models.memory });
  },

  async removeSpecial(ctx) {
    const { id } = ctx.params;
    let entity;
    entity = await strapi.services.memory.update({ id }, {$pull : {collections: ctx.request.body.idCollection}});
    return sanitizeEntity(entity, { model: strapi.models.memory });
  },
  
};