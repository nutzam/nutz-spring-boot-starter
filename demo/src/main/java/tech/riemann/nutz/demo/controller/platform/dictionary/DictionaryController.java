package tech.riemann.nutz.demo.controller.platform.dictionary;

import org.nutz.lang.Lang;
import org.nutz.spring.boot.service.entity.Pagination;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import tech.riemann.nutz.demo.entity.dictionary.Dictionary;
import tech.riemann.nutz.demo.service.dictionary.DictionaryService;

/**
 * <p>
 * 码本数据 前端控制器
 * </p>
 *
 * @author Kerbores(kerbores@gmail.com)
 *
 * @since 2022-09-09 23:34:36
 */
@RestController
@RequiredArgsConstructor
@Tag(name = "Dictionary", description = "码本数据")
public class DictionaryController {

    private final DictionaryService dictionaryService;

    /**
     * 分页查询码本数据
     * 
     * @param page
     *            页码
     * @param size
     *            分页大小
     * @param key
     *            关键词
     * @return 码本数据分页数据
     */
    @GetMapping("dictionarys")
    @Operation(summary = "分页查询码本数据")
    public Pagination<Dictionary> dictionarys(
                                              @Parameter(description = "页码") @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                                              @Parameter(description = "页面大小") @RequestParam(value = "size", required = false, defaultValue = "10") int size,
                                              @Parameter(description = "搜索关键词") @RequestParam(name = "key", required = false, defaultValue = "") String key) {
        return dictionaryService.searchByKeyAndPage(key,
                                                    page,
                                                    size,
                                                    Dictionary.Fields.value);
    }

    /**
     * 码本数据详情
     * 
     * @param id
     *            码本数据id
     * @return 码本数据
     */
    @GetMapping("dictionary/{id}")
    @Operation(summary = "码本数据详情")
    public Dictionary dictionaryDetail(@Parameter(description = "码本数据id", required = true) @PathVariable("id") long id) {
        return dictionaryService.fetch(id);
    }

    /**
     * 添加或者更新码本数据
     * 
     * @param dictionary
     *            码本数据数据
     * @return 码本数据
     */
    @PutMapping("dictionary")
    @Operation(summary = "增加/编辑码本数据")
    public Dictionary saveOrUpdateDictionary(@Validated @Parameter(description = "码本数据") @RequestBody Dictionary dictionary) {
        if (dictionary.getId() != null && dictionary.getId() > 0) {
            if (dictionaryService.update(dictionary) == 1) {
                return dictionary;
            } else {
                throw Lang.makeThrow("更新码本数据失败!");
            }
        }
        return dictionaryService.insert(dictionary);
    }

    /**
     * 删除码本数据
     * 
     * @param id
     *            码本数据id
     * @return 是否删除成功
     */
    @DeleteMapping("dictionary/{id}")
    @Operation(summary = "删除码本数据")
    @ResponseStatus(HttpStatus.OK)
    public void deleteDictionary(@Parameter(description = "码本数据id", required = true) @PathVariable("id") long id) {
        if (dictionaryService.delete(id) != 1) {
            throw Lang.makeThrow("删除码本数据失败!");
        }
    }

}
